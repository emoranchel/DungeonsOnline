package net.dungeons.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.dungeons.model.Combatant;

@RequestScoped
@Named("combatCharActions")
public class CombatCharActions {

    @Inject
    private SessionData data;

    public String update(Combatant combatant) {
        return update(combatant.getName());
    }

    public String update(String combatantId) {
        data.getCombat().updateCombatant(combatantId);
        return null;
    }

    public String remove(Combatant character) {
        return remove(character.getName());
    }

    public String remove(String combatant) {
        data.getCombat().removeCombatant(combatant);
        return null;
    }

    public String dodamage(String character) {
        Combatant combatant = data.getCombat().getCombatants().get(character);
        combatant.damage();
        data.getCombat().updateCombatant(combatant.getName());
        return null;
    }

    public String doheal(String character) {
        Combatant combatant = data.getCombat().getCombatants().get(character);
        combatant.heal();
        data.getCombat().updateCombatant(combatant.getName());
        return null;
    }
}
