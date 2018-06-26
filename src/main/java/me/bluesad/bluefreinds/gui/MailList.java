package me.bluesad.bluefreinds.gui;

import me.bluesad.bluefreinds.manager.Individual;
import me.bluesad.bluefreinds.manager.Mail;
import org.bluesad.tablet.component.TabletCollectionList;
import org.bluesad.tablet.component.TabletGui;
import org.bluesad.tablet.component.TabletSubGui;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class MailList extends TabletCollectionList<Mail> {
    public MailList(TabletGui connectedGui, ConfigurationSection section){
        super(connectedGui,section);
    }

    @Override
    protected void onRequest(Mail mail, TabletSubGui tabletSubGui) {
        tabletSubGui.getAttributeMap().setExtraFunction(str->mail.replace(str));
    }

    @Override
    protected List<Mail> getCollection() {
        return new Individual(getAngle()).getMailList();
    }
}
