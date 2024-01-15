package com.mowitnow.mower.domain;


import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>
 * Objects that have a distinct identity that runs through time and different
 * representations. You also hear these called "reference objects".
 * </p>
 * <p>
 * The methods equals and hashcode of an entity must only consider its id and no
 * other properties.
 * </p>
 *
 * @see <a href="http://martinfowler.com/bliki/EvansClassification.html">Evan's
 * Classification</a>
 */
@Retention(RetentionPolicy.SOURCE)
@Inherited
@Documented
public @interface Entity {
}
