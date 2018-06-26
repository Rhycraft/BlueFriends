package me.bluesad.bluefreinds.gui;

import me.bluesad.bluefreinds.manager.Individual;
import org.bluesad.tablet.component.TabletCollectionList;
import org.bluesad.tablet.component.TabletGui;
import org.bluesad.tablet.component.TabletSubGui;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class FriendList extends TabletCollectionList<Individual> {
    public FriendList(TabletGui connectedGui, ConfigurationSection section){
        super(connectedGui,section);
    }

    @Override
    protected void onRequest(Individual individual, TabletSubGui tabletSubGui) {
        tabletSubGui.setAngle(Bukkit.getOfflinePlayer(individual.getUniqueId()));
    }

    @Override
    protected List<Individual> getCollection() {
        return new Individual(getAngle()).getIndividualFriendList();
    }
}
