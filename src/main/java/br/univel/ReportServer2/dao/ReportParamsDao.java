package br.univel.ReportServer2.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import br.univel.ReportServer2.model.ReportParams;

/**
 *  DAO for ReportParams
 */
@Stateless
public class ReportParamsDao
{
   @PersistenceContext(unitName = "ReportServer2-persistence-unit")
   private EntityManager em;

   public void create(ReportParams entity)
   {
      em.persist(entity);
   }

   public void deleteById(Long id)
   {
      ReportParams entity = em.find(ReportParams.class, id);
      if (entity != null)
      {
         em.remove(entity);
      }
   }

   public ReportParams findById(Long id)
   {
      return em.find(ReportParams.class, id);
   }

   public ReportParams update(ReportParams entity)
   {
      return em.merge(entity);
   }

   public List<ReportParams> listAll(Integer startPosition, Integer maxResult)
   {
      TypedQuery<ReportParams> findAllQuery = em.createQuery("SELECT DISTINCT r FROM ReportParams r ORDER BY r.id", ReportParams.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      return findAllQuery.getResultList();
   }
}
