package net.dungeons.jsf.data;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import net.dungeons.data.DataClass;

@Named("adminDataClasses")
@RequestScoped
public class AdminDataClasses {

  @PersistenceContext
  private EntityManager em;
  private List<DataClass> classes;

  @PostConstruct
  public void init() {
    CriteriaQuery<DataClass> cqClass = em.getCriteriaBuilder().createQuery(DataClass.class);
    classes = em.createQuery(cqClass.select(cqClass.from(DataClass.class))).getResultList();
  }

  public List<DataClass> getClasses() {
    return classes;
  }

  @Transactional
  public void save() {
    classes.forEach(this::save);
  }

  @Transactional
  private void save(Object c) {
    em.merge(c);
  }

}
