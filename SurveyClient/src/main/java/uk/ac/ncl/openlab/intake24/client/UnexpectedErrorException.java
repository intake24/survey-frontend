/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client;

import java.io.Serializable;

/**
 * Indicates that the log in procedure has failed due to an unexpected exception
 * (network problem etc.)
 */
public class UnexpectedErrorException extends Exception implements Serializable {
	private static final long serialVersionUID = -8760470844891288456L;
}
