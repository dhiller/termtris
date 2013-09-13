package de.dhiller.termtris.map;

import com.google.common.base.Joiner;

/**
 * Thrown when a tile is added to or moved within a map when another tile occupies any cell the new tile would occupy.
 *
 * @author dhiller.
 */
public class CollisionException extends RuntimeException {

    public CollisionException(Map.RawMapData collisions) {
        super("Colliding points: "+collisions.getCollisions()+"\nMap:\n"+ Joiner.on("\n").join(collisions));
    }
}
