Como funciona codely es que el caso de uso nunca devuelve la entidad del dominio y siempre devuelve un objeto response para que nadie tenga la tentación de coger el objeto de dominio manipularle e inyectar un repo para hacer alguna acción.

Esto implica que los casos de uso genéricos tuviera un mapper para pasar del entity al response.

Pendiente meter mapstruct