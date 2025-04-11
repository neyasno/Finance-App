package org.example.notificationservice.constants;

import java.util.List;

public interface LinkAttachable {
    public List<LinkAttachment> getAttachedLinks();
    public void setAttachedLinks(List<LinkAttachment> attachmentLinks);
    public void addAttachedLink(LinkAttachment attachment);
    public void removeLinkAttachments();

}
