/*
 * Copyright (C) 2000 - 2012 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection withWriter Free/Libre
 * Open Source Software ("FLOSS") applications as described in Silverpeas's
 * FLOSS exception.  You should have recieved a copy of the text describing
 * the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/legal/licensing"
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.silverpeas.profile.web;

import static com.silverpeas.profile.web.ProfileResourceBaseURIs.*;
import static com.silverpeas.util.StringUtil.isDefined;
import com.silverpeas.web.Selectable;
import com.stratelia.webactiv.beans.admin.Group;
import java.net.URI;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A user group that is selectable. It is a web entity representing a group of users that can be
 * selected among others in order to participate to a given action in the Silverpeas portal. It is a
 * decorator that decorates a Group object with additional properties concerning the selection.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class SelectableUserGroup extends Group implements Selectable {

  private static final long serialVersionUID = 6383835034479351000L;

  /**
   * Decorates the specified user group with selectable features. By default, the decorated group
   * isn't selected.
   *
   * @param group the user group to decorate.
   * @return a selectable user group, not selected by default.
   */
  public static SelectableUserGroup fromGroup(final Group group) {
    return new SelectableUserGroup(group);
  }

  /**
   * Decorates the specified user groups with selectable features. By default, the decorated groups
   * aren't selected.
   *
   * @param users a list of user groups to decorate.
   * @param groupsURI the URI at which the specified groups are defined.
   * @return a list of selectable user groups, not selected by default.
   */
  public static SelectableUserGroup[] fromGroups(final List<? extends Group> groups, URI groupsURI) {
    SelectableUserGroup[] selectableGroups = new SelectableUserGroup[groups.size()];
    String fromGroupsUri = groupsURI.toString();
    int i = 0;
    for (Group aGroup : groups) {
      selectableGroups[i++] = fromGroup(aGroup).withAsUri(uriOfGroup(aGroup, fromGroupsUri));
    }
    return selectableGroups;
  }
  @XmlElement
  private boolean selected = false;
  @XmlElement
  private URI uri;
  @XmlElement
  private URI parentUri;
  @XmlElement
  private URI childrenUri;
  @XmlElement
  private URI usersUri;
  @XmlElement
  private int userCount = -1;
  @XmlElement
  private String domainName;
  private final Group group;

  private SelectableUserGroup(Group group) {
    this.group = group;
    this.domainName = Group.getOrganizationController().getDomain(group.getDomainId()).getName();
    this.userCount = group.getTotalNbUsers();
  }

  protected SelectableUserGroup() {
    this.group = new Group();
  }

  public SelectableUserGroup withAsUri(URI groupUri) {
    this.uri = groupUri;
    this.childrenUri = computeChildrenUriOfGroupByUri(groupUri);
    if (isDefined(getSuperGroupId())) {
      this.parentUri = computeParentUriOfGroupByUri(groupUri);
    }
    this.usersUri = computeUsersUriOfGroupById(getId());
    return this;
  }

  /**
   * Gets the URI of this group in Silverpeas.
   *
   * @return the unique resource identifier of this group.
   */
  public URI getUri() {
    return uri;
  }

  /**
   * Gets the URI of its parent group.
   *
   * @return the URI of its parent group or null if this group is a root one.
   */
  public URI getParentUri() {
    return parentUri;
  }

  /**
   * Gets the URI at which its direct children groups can be retrieved.
   *
   * @return the URI at which its subgroups can be get.
   */
  public URI getChildrenUri() {
    return childrenUri;
  }

  public URI getUsersUri() {
    return usersUri;
  }

  @Override
  public boolean isSelected() {
    return this.selected;
  }

  @Override
  public void select() {
    this.selected = true;
  }

  @Override
  public void unselect() {
    this.selected = false;
  }

  @Override
  @XmlElement
  public String getDescription() {
    return this.group.getDescription();
  }

  @Override
  @XmlElement
  public String getId() {
    return this.group.getId();
  }

  @Override
  @XmlElement
  public String getName() {
    return this.group.getName();
  }

  @Override
  public int getNbUsers() {
    return this.group.getNbUsers();
  }

  @Override
  public int getTotalNbUsers() {
    if (userCount == -1) {
      userCount = this.group.getTotalNbUsers();
    }
    return userCount;
  }

  @Override
  @XmlElement
  public String getSpecificId() {
    return this.group.getSpecificId();
  }

  @Override
  public void setDescription(String newDescription) {
    this.group.setDescription(newDescription);
  }

  @Override
  public void setId(String newId) {
    this.group.setId(newId);
  }

  @Override
  public void setName(String newName) {
    this.group.setName(newName);
  }

  @Override
  public void setNbUsers(int nbUsers) {
    this.group.setNbUsers(nbUsers);
  }

  @Override
  @XmlElement
  public String getDomainId() {
    return this.group.getDomainId();
  }

  @Override
  @XmlElement
  public String getSuperGroupId() {
    return this.group.getSuperGroupId();
  }

  @Override
  public void setDomainId(String newDomainId) {
    this.group.setDomainId(newDomainId);
    this.domainName = Group.getOrganizationController().getDomain(newDomainId).getName();
  }

  @Override
  public void setSpecificId(String newSpecificId) {
    this.group.setSpecificId(newSpecificId);
  }

  @Override
  public void setSuperGroupId(String newSuperGroupId) {
    this.group.setSuperGroupId(newSuperGroupId);
  }

  public String getDomainName() {
    return this.domainName;
  }

  @Override
  public List<? extends Group> getSubGroups() {
    return this.group.getSubGroups();
  }

  @Override
  public void traceGroup() {
    group.traceGroup();
  }

  @Override
  public void setUserIds(String[] sUserIds) {
    group.setUserIds(sUserIds);
  }

  @Override
  public void setRule(String rule) {
    group.setRule(rule);
  }

  @Override
  public boolean isSynchronized() {
    return group.isSynchronized();
  }

  @Override
  public boolean isRoot() {
    return group.isRoot();
  }

  @Override
  public String[] getUserIds() {
    return group.getUserIds();
  }

  @Override
  public String getRule() {
    return group.getRule();
  }

  @Override
  public int compareTo(Group o) {
    return group.compareTo(o);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SelectableUserGroup) {
      return this.group.equals(((SelectableUserGroup) obj).group);
    } else {
      return this.group.equals(obj);
    }
  }

  @Override
  public int hashCode() {
    return this.group.hashCode();
  }
}