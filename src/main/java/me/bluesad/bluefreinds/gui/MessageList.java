package me.bluesad.bluefreinds.gui;

import me.bluesad.bluefreinds.manager.Individual;
import org.bluesad.tablet.component.TabletCollectionList;
import org.bluesad.tablet.component.TabletGui;
import org.bluesad.tablet.component.TabletSubGui;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class MessageList extends TabletCollectionList<String> {
    public MessageList(TabletGui connectedGui, ConfigurationSection section){
        super(connectedGui,section);
    }

    @Override
    protected void onRequest(String s, TabletSubGui tabletSubGui) {
        tabletSubGui.getAttributeMap().setExtraFunction(str->str.replaceAll("%message%",s).replaceAll("%index%",String.valueOf(getCollection().indexOf(s))));
    }

    @Override
    protected List<String> getCollection() {
        return new Individual(getAngle()).getSystemMessageList();
    }
}
