package net.dungeons.jsf.data;

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
import net.dungeons.data.CharaDetail;
import net.dungeons.data.CharaFeat;

@Named("adminCharacters")
@RequestScoped
public class AdminCharacters {

  @PersistenceContext
  private EntityManager em;
  private List<Chara> chars;
  private List<CharaDetail> details;

  @PostConstruct
  public void init() {
    CriteriaQuery<Chara> cqChara = em.getCriteriaBuilder().createQuery(Chara.class);
    chars = em.createQuery(cqChara.select(cqChara.from(Chara.class))).getResultList();

    CriteriaQuery<CharaDetail> cqDetail = em.getCriteriaBuilder().createQuery(CharaDetail.class);
    details = em.createQuery(cqDetail.select(cqDetail.from(CharaDetail.class))).getResultList();
  }

  public List<Chara> getCharacters() {
    return chars;
  }

  public List<CharaDetail> getDetails() {
    return details;
  }

  public List<String> getTypes() {
    return Arrays.asList(new String[]{"team", "npc", "player"});
  }

  @Transactional
  public void saveAll() {
    chars.forEach(this::saveChar);
    details.forEach(this::saveDetail);
  }

  @Transactional
  public void saveChar(Chara c) {
    em.merge(c);
  }

  @Transactional
  public void saveDetail(CharaDetail c) {
    em.merge(c);
  }

  @Transactional
  public void removeFeat(Chara cha, CharaFeat bonus) {
    cha.getBonuses().remove(bonus);
    em.merge(cha);
    em.remove(em.find(CharaFeat.class, bonus.getId()));
  }

}
