package net.dungeons.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CHARDETAIL")
public class CharaDetail implements Serializable {

  @Id
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 20)
  @Column(name = "NAME")
  private String name;

  @MapsId
  @OneToOne(mappedBy = "charDetail")
  @JoinColumn(name = "NAME")
  private Chara character;

  @Basic(optional = true)
  @Column(length = 30)
  private String alignment;

  @Basic(optional = true)
  @Column(length = 20)
  private String csize;

  @Basic(optional = true)
  @Column(length = 20)
  private String weight;
  @Basic(optional = true)
  @Column(length = 20)
  private String height;
  @Basic(optional = true)
  @Column(length = 20)
  private String age;

  @Basic(optional = true)
  @Column(length = 800)
  private String description;

  @Basic(optional = true)
  @Column(length = 800)
  private String personality;

  @Basic(optional = true)
  @Column(length = 800)
  private String background;

  @Basic(optional = true)
  @Column(length = 800)
  private String family;

  @Basic(optional = true)
  @Column(length = 800)
  private String relations;

  @Basic(optional = true)
  @Column(length = 1400)
  private String journal;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Chara getCharacter() {
    return character;
  }

  public void setCharacter(Chara character) {
    this.character = character;
  }

  public String getAlignment() {
    return alignment;
  }

  public void setAlignment(String alignment) {
    this.alignment = alignment;
  }

  public String getCsize() {
    return csize;
  }

  public void setCsize(String csize) {
    this.csize = csize;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPersonality() {
    return personality;
  }

  public void setPersonality(String personality) {
    this.personality = personality;
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String background) {
    this.background = background;
  }

  public String getFamily() {
    return family;
  }

  public void setFamily(String family) {
    this.family = family;
  }

  public String getRelations() {
    return relations;
  }

  public void setRelations(String relations) {
    this.relations = relations;
  }

  public String getJournal() {
    return journal;
  }

  public void setJournal(String journal) {
    this.journal = journal;
  }

}
