/*
 * Copyright (C) 2000 - 2011 Silverpeas
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
package com.silverpeas.pdc.dao;

import com.silverpeas.pdc.model.PdcClassification;
import org.synyx.hades.dao.GenericDao;

/**
 * DAO that handles the persistence of PdcClassification beans.
 */
public interface PdcClassificationDAO extends GenericDao<PdcClassification, Long> {

  /**
   * Finds the predefined classification on the PdC that is set for the whole specified component
   * instance.
   * @param instanceId the unique identifier of the component instance.
   * @return the predefined classification that is set to the component instance, or null if no
   * predefined classification was set for the component instance.
   */
  PdcClassification findPredefinedClassificationByComponentInstanceId(String instanceId);
  
  /**
   * Finds the predefined classification on the PdC that is set for the contents in the specified
   * node of the specified component instance.
   * @param nodeId the unique identifier of the node.
   * @param instanceId the unique identifier of the component instance to which the node belongs.
   * @return either the predefined classification associated with the node or null if no predefined 
   * classification exists for that node.
   */
  PdcClassification findPredefinedClassificationByNodeId(String nodeId, String instanceId);
  
}