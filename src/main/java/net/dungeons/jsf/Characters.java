package net.dungeons.jsf;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import net.dungeons.data.Chara;
import net.dungeons.model.CampaignCharacter;

@Named
@RequestScoped
public class Characters {

  @PersistenceContext
  private EntityManager em;
  private List<CampaignCharacter> characters;

  @PostConstruct
  public void init() {
    CriteriaQuery<Chara> cq = em.getCriteriaBuilder().createQuery(Chara.class);
    this.characters = em.createQuery(cq.select(cq.from(Chara.class)))
            .getResultList().stream()
            .parallel()
            .map(CampaignCharacter::new)
            .collect(Collectors.toList());
  }

  public List<CampaignCharacter> getCList() {
    return characters;
  }
}
