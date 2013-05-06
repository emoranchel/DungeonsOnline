package net.dungeons.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Combatants implements Iterable<Combatant> {

    private final List<Combatant> shownCombatants;
    private final List<Combatant> hiddenCombatants;
    private final List<Combatant> all;
    private final Set<String> allIds;

    public Combatants() {
        shownCombatants = new ArrayList<>();
        hiddenCombatants = new ArrayList<>();
        all = new ArrayList<>();
        allIds = new HashSet<>();
    }

    void clear() {
        hiddenCombatants.clear();
        shownCombatants.clear();
        all.clear();
        allIds.clear();
    }

    public Combatant hide(String combatantId) {
        for (Iterator<Combatant> iter = shownCombatants.iterator(); iter.hasNext();) {
            Combatant combatant = iter.next();
            if (combatant.getName().equals(combatantId)) {
                iter.remove();
                hiddenCombatants.add(combatant);
                combatant.setHidden(true);
                return combatant;
            }
        }
        return null;
    }

    public Combatant show(String combatantId) {
        for (Iterator<Combatant> iter = hiddenCombatants.iterator(); iter.hasNext();) {
            Combatant combatant = iter.next();
            if (combatant.getName().equals(combatantId)) {
                iter.remove();
                shownCombatants.add(combatant);
                combatant.setHidden(false);
                return combatant;
            }
        }
        return null;
    }

    public Combatant addHidden(Combatant combatant) {
        if (!allIds.contains(combatant.getName())) {
            allIds.add(combatant.getName());
            all.add(combatant);
            hiddenCombatants.add(combatant);
            combatant.setHidden(true);
            return combatant;
        }
        return null;
    }

    public Combatant addVisible(Combatant combatant) {
        if (!allIds.contains(combatant.getName())) {
            allIds.add(combatant.getName());
            all.add(combatant);
            shownCombatants.add(combatant);
            combatant.setHidden(false);
            return combatant;
        }
        return null;
    }

    public Combatant remove(String combatantId) {
        allIds.remove(combatantId);
        for (Iterator<Combatant> iter = all.iterator(); iter.hasNext();) {
            Combatant combatant = iter.next();
            if (combatant.getName().equals(combatantId)) {
                iter.remove();
            }
        }
        for (Iterator<Combatant> iter = shownCombatants.iterator(); iter.hasNext();) {
            Combatant combatant = iter.next();
            if (combatant.getName().equals(combatantId)) {
                iter.remove();
                return combatant;
            }
        }
        for (Iterator<Combatant> iter = hiddenCombatants.iterator(); iter.hasNext();) {
            Combatant combatant = iter.next();
            if (combatant.getName().equals(combatantId)) {
                iter.remove();
                return null;
            }
        }
        return null;
    }

    public Combatant get(String combatantId) {
        Combatant shownCombatant = getCombatant(combatantId);
        if (shownCombatant != null) {
            return shownCombatant;
        }
        for (Combatant hiddenCombatant : hiddenCombatants) {
            if (hiddenCombatant.getName().equals(combatantId)) {
                return hiddenCombatant;
            }
        }
        return null;
    }

    public Combatant getCombatant(String name) {
        for (Combatant combatant : shownCombatants) {
            if (combatant.getName().equals(name)) {
                return combatant;
            }
        }
        return null;
    }

    public Set<String> getAllIds() {
        return allIds;
    }

    public List<Combatant> getCombatants() {
        return shownCombatants;
    }

    public List<Combatant> getHiddenCombatants() {
        return hiddenCombatants;
    }

    public List<Combatant> getAll() {
        return all;
    }

    @Override
    public Iterator<Combatant> iterator() {
        return shownCombatants.iterator();
    }
}
