package me.bluesad.bluefreinds.gui;

import me.bluesad.bluefreinds.manager.Individual;
import org.bluesad.tablet.component.TabletCollectionList;
import org.bluesad.tablet.component.TabletGui;
import org.bluesad.tablet.component.TabletSubGui;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class RequestList extends TabletCollectionList<String> {
    public RequestList(TabletGui connectedGui, ConfigurationSection section){
        super(connectedGui,section);
    }

    @Override
    protected void onRequest(String s, TabletSubGui tabletSubGui) {
        tabletSubGui.getAttributeMap().setExtraFunction(str->new Individual(s).replace(str));
    }

    @Override
    protected List<String> getCollection() {
        return new Individual(getAngle()).getRequestList();
    }
}
