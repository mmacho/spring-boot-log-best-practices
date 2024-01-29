package com.example.demo.service.support.v1;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.BaseEntity_;
import com.example.demo.repository.GenericRepository;
import com.example.demo.service.exception.ResourceNotFoundException;
import com.example.demo.service.exception.StaleStateIdentifiedException;

import io.github.perplexhub.rsql.RSQLJPASupport;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public abstract class GenericService<T extends BaseEntity, ID extends Serializable> {

    private final GenericRepository<T, ID> repository;

    public Page<T> search(Integer page, Integer size, String filter, String sort) {
		final Specification<T> specification = RSQLJPASupport.<T>toSpecification(filter, Boolean.TRUE)
				.and(RSQLJPASupport.<T>toSort(sort));
		final Pageable pageable = PageRequest.of(page, size);
		return this.repository.findAll(specification, pageable);
    }

    public List<T> findAll() {
		return this.repository.findAll();
    }

    public T findbyId(@NonNull final ID id) throws ResourceNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> ResourceNotFoundException
				.forAggregateWith(MessageFormat.format("Entity not found for this id {0}", id)));
    }

    @Transactional
	public T create(@NonNull T domain) {
        return this.repository.save(domain);
	}

	@Transactional
	public T update(@NonNull final ID id, @NonNull T domain) throws ResourceNotFoundException {
		T entity = findbyId(id);
		BeanUtils.copyProperties(domain, entity, BaseEntity_.ID, BaseEntity_.CREATED_AT, BaseEntity_.MODIFIED_AT);
		try {
			return this.repository.save(entity);
		} catch (OptimisticLockingFailureException e) {
			throw StaleStateIdentifiedException
					.forAggregateWith(MessageFormat.format("Confict to update entity with id {0}", id));
		}
	}


	@Transactional
	public void delete(@NonNull final ID id) throws ResourceNotFoundException {
		T entity = findbyId(id);
		this.repository.delete(entity);
	}

}
