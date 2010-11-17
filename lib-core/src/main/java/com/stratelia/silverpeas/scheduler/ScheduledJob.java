/*
 * Copyright (C) 2000 - 2009 Silverpeas
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * As a special exception to the terms and conditions of version 3.0 of
 * the GPL, you may redistribute this Program in connection with Free/Libre
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

package com.stratelia.silverpeas.scheduler;

import com.stratelia.silverpeas.scheduler.trigger.JobTrigger;

/**
 * A job that is scheduled in the scheduler.
 * 
 * A job registered in the scheduler is instanciated into a ScheduledJob object that carries all of
 * the information required by the scheduler to perform its task.
 * The implementation of this interface depends upon the scheduling backend in use.
 */
public interface ScheduledJob {

  /**
   * Gets the name under which the job is registered into the scheduler.
   * @return 
   */
  String getName();
  
  /**
   * Gets the trigger responsible of firing the execution of this job.
   * @return the trigger of this job.
   */
  JobTrigger getTrigger();

  /**
   * Gets the listener of the scheduler's events mapped with the state of this job execution.
   * @return a scheduler's events listener registered with this job.
   */
  SchedulerEventListener getSchedulerEventListener();
  
  /**
   * Executes the job with the specified execution context.
   * The context carries the information that can be required by the job to fulfill its execution,
   * like the job parameters.
   * @param context the context under which this job is executed.
   * @throws Exception if an error occurs during the job execution.
   */
  void execute(final JobExecutionContext context) throws Exception;
}