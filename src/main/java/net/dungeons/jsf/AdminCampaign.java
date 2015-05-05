package net.dungeons.jsf;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import net.dungeons.data.Chara;

@Named("adminCampaign")
@RequestScoped
public class AdminCampaign {

  @PersistenceContext
  private EntityManager em;
  private List<Chara> chars;

  @PostConstruct
  public void init() {
    CriteriaQuery<Chara> cq = em.getCriteriaBuilder().createQuery(Chara.class);
    chars = em.createQuery(cq.select(cq.from(Chara.class))).getResultList();
  }

  public List<Chara> getCharacters() {
    return chars;
  }

  public List<String> getTypes() {
    return Arrays.asList(new String[]{"team", "npc", "player"});
  }

  @Transactional
  public void saveAll() {
    chars.forEach(this::save);
  }

  @Transactional
  public void save(Chara c) {
    c = em.merge(c);
    em.persist(c);
  }
}
