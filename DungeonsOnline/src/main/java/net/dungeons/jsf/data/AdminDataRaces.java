package net.dungeons.jsf.data;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import net.dungeons.data.DataRace;

@Named("adminDataRaces")
@RequestScoped
public class AdminDataRaces {

  @PersistenceContext
  private EntityManager em;
  private List<DataRace> classes;

  @PostConstruct
  public void init() {
    CriteriaQuery<DataRace> cqClass = em.getCriteriaBuilder().createQuery(DataRace.class);
    classes = em.createQuery(cqClass.select(cqClass.from(DataRace.class))).getResultList();
  }

  public List<DataRace> getRaces() {
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
