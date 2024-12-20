package br.com.dtidigital.mentoriabackend.domain.repository.helper.pessoa;

import br.com.dtidigital.mentoriabackend.api.v1.model.filter.PessoaFiltro;
import br.com.dtidigital.mentoriabackend.domain.entity.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

// TODO: Refatorar para adicionar função de adicionar filtro
// TODO: Fazer um novo endpoint para buscar por ID
// TODO: LER SOBRE O DESIGN PATTERN BUILDER

@Repository
public class PessoaRepositoryImpl implements PessoaQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Pessoa filtro(PessoaFiltro pessoaFiltro) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Pessoa p WHERE 1=1");
        Query query = null;
        if(pessoaFiltro != null){
            if(pessoaFiltro.apelido() != null){
                jpql.append(" AND p.apelido = :apelido");
            }
            if(pessoaFiltro.nome() != null){
                jpql.append(" AND p.nome = :nome");
            }
            if(pessoaFiltro.dataNascimento() != null){
                jpql.append(" AND p.dataNascimento = :dataNascimento");
            }
            query = entityManager.createQuery(jpql.toString(), Pessoa.class);

            if(pessoaFiltro.apelido() != null){
                query.setParameter("apelido", pessoaFiltro.apelido());

            }
            if(pessoaFiltro.nome() != null){
                query.setParameter("nome", pessoaFiltro.nome());
            }
            if(pessoaFiltro.dataNascimento() != null){
                query.setParameter("dataNascimento", pessoaFiltro.dataNascimento());
            }
        }
        if(!query.getResultList().isEmpty()) {
            return (Pessoa) query.getResultList().get(0);
        }
        return null;
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