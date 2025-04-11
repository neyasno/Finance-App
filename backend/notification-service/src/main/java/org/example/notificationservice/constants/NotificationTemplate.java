package org.example.notificationservice.constants;

import java.util.ArrayList;
import java.util.List;

public abstract class NotificationTemplate implements LinkAttachable {
    protected ArrayList<LinkAttachment> attachedLinks;
    protected final String defaultTemplate = null;
    public abstract String getNotificationString();

    @Override
    public ArrayList<LinkAttachment> getAttachedLinks() {
        return this.attachedLinks;
    }

    @Override
    public void setAttachedLinks(List<LinkAttachment> attachedLinks) {
        this.attachedLinks = new ArrayList<LinkAttachment>(attachedLinks);
    }

    @Override
    public void addAttachedLink(LinkAttachment attachment) {
        this.attachedLinks.add(attachment);
    }

    @Override
    public void removeLinkAttachments() {
        this.attachedLinks = null;
    }
}
