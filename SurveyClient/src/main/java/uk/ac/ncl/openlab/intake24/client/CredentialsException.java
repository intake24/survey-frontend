/*
This file is part of Intake24.

© Crown copyright, 2012, 2013, 2014.

This software is licensed under the Open Government Licence 3.0:

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client;

import java.io.Serializable;

/**
 * Indicates that either the supplied user name is not known
 * or the password is incorrect.
 */
public class CredentialsException extends Exception implements Serializable {
	private static final long serialVersionUID = 1462684812100096518L;

}
