/**
 * The Rhymestore project.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package com.rhymestore.twitter.commands;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.rhymestore.twitter.TwitterScheduler;

/**
 * Executes Twitter API calls.
 * 
 * @author Ignasi Barrera
 * 
 * @see TwitterScheduler
 */
public interface TwitterCommand
{
	/**
	 * Executes a Twitter API call in the given account.
	 * 
	 * @param twitter The Twitter account to use.
	 * @throws If the API call returns an error.
	 */
	public void execute(Twitter twitter) throws TwitterException;
}
