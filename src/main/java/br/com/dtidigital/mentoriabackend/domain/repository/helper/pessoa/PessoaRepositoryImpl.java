package br.com.dtidigital.mentoriabackend.domain.repository.helper.pessoa;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PessoaRepositoryImpl implements PessoaQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Pessoa filtro(PessoaFiltro pessoaFiltro) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Pessoa p WHERE 1=1");
        Query query = entityManager.createQuery(jpql.toString(), Pessoa.class);
        if (pessoaFiltro != null) {
            adicionarFiltros(pessoaFiltro, jpql, query);
        }
        if (!query.getResultList().isEmpty()) {
            return (Pessoa) query.getResultList().get(0);
        }
        return null;
    }

    private void adicionarFiltros(PessoaFiltro pessoaFiltro, StringBuilder jpql, Query query) {
        Map<String, Object> filtros = new HashMap<>();

        if (pessoaFiltro.apelido() != null) {
            jpql.append(" AND p.apelido = :apelido");
            filtros.put("apelido", pessoaFiltro.apelido());
        }
        if (pessoaFiltro.nome() != null) {
            jpql.append(" AND p.nome = :nome");
            filtros.put("nome", pessoaFiltro.nome());
        }
        if (pessoaFiltro.dataNascimento() != null) {
            jpql.append(" AND p.dataNascimento = :dataNascimento");
            filtros.put("dataNascimento", pessoaFiltro.dataNascimento());
        }

        addParameters(jpql, filtros);
    }

    private void addParameters(StringBuilder jpql, Map<String, Object> filtros) {
        Query query;
        query = entityManager.createQuery(jpql.toString(), Pessoa.class);
        for (Map.Entry<String, Object> entry : filtros.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
}



// EXEMPLO DE USO DO CRITERIA
//public List<PlanoCobertura> listPlanoCobertura(int produtoId, int coberturaId, int planoId){
//    log.trace("Inicio");
//    List<PlanoCobertura> list = null;
//
////		Criteria criteria = createCriteria();
//    DetachedCriteria criteria = createDetachedCriteria();
//
//    criteria.add(Restrictions.eq("produto.id", produtoId));
//    criteria.add(Restrictions.eq("cobertura.id", coberturaId));
//    criteria.add(Restrictions.eq("plano.id", planoId));
//
//    criteria.addOrder(Order.asc("dtFimVig"));
//    criteria.addOrder(Order.asc("dtIniVig"));
//
//    list = getHibernateTemplate().findByCriteria(criteria);
//    log.trace("fim");
//    return list;
//
//}