package web.onficina.repository.pagination;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

public class PaginacaoUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PaginacaoUtil.class);

	public static void prepararIntervalo(TypedQuery<?> typedQuery, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;
		logger.info("Filtrando a página {}, registros entre {} e {}", paginaAtual, primeiroRegistro, primeiroRegistro + totalRegistrosPorPagina);
		typedQuery.setFirstResult(primeiroRegistro);
		typedQuery.setMaxResults(totalRegistrosPorPagina);
	}
	
	public static void prepararOrdemCriteria(Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder, Pageable pageable) {
		String atributo;
		Sort sort = pageable.getSort();
		Order order;
		List<Order> ordenacoes = new ArrayList<>();
		if (sort != null && !sort.isEmpty()) {
			for (Sort.Order o : sort) {
				logger.info("Ordenando o resultado da pesquisa por {}, {}", o.getProperty(), o.getDirection());
			    atributo = o.getProperty();
			    order = o.isAscending() ? builder.asc(root.get(atributo)) : builder.desc(root.get(atributo));
			    ordenacoes.add(order);
			}
		}
		criteriaQuery.orderBy(ordenacoes);
	}

	public static void prepararOrdemJPQL(StringBuilder query, Pageable pageable) {
		String atributo;
		Sort sort = pageable.getSort();
		boolean maisDeUm = false;
		if (sort != null && !sort.isEmpty()) {
			query.append(" order by ");
			for (Sort.Order o : sort) {
				logger.info("Ordenando o resultado da pesquisa por {}, {}", o.getProperty(), o.getDirection());
				if (maisDeUm) {
					query.append(", ");	
				}
			    atributo = o.getProperty();
				query.append(atributo);
				query.append(o.isAscending() ? " asc" : " desc");
				maisDeUm = true;
			}
		}
	}
	
	//A ideia era boa, mas não funciona.
	//O JPA reclama pq a lista de predicados foi criada usando outra Root que não a 
	// criada dentro do método.
	//Não encontrei uma maneira de deixar isso genérico, tem que repetir em todo lugar
	// que usar paginação
	// public static <T> long getTotalRegistros(Class<T> clazz, Predicate[] predicateArray, CriteriaBuilder builder, EntityManager manager) {
	// 	logger.info("Calculando o total de registros que o filtro retornará.");
	// 	CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
	// 	Root<T> root = criteriaQuery.from(clazz);
	// 	criteriaQuery.select(builder.count(root));
	// 	criteriaQuery.where(predicateArray);
	// 	TypedQuery<Long> typedQueryTotal = manager.createQuery(criteriaQuery);
	// 	long totalRegistros = typedQueryTotal.getSingleResult();
	// 	logger.info("O filtro retornará {} registros.", totalRegistros);	
	// 	return totalRegistros;
	// }
}
