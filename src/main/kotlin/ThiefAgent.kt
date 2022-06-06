import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.Agent
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.simulation.entity.MovingEntity
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.geometry.Point
import kotlin.concurrent.thread
import kotlin.random.Random


class ThiefAgent() : Agent() {
    private var wanttosteal = false
    override fun onBirth() {
        speed = 1.0
    }
    private var lastPosition = Point(-1, -1)

    override fun pluginUpdate() {
        if (!wanttosteal) {
            // looks around and creates a list with all entities around the thief
            @Suppress("unchecked_cast") val entitiesInRadius = world.entities.filter {
                it is MovingEntity && position..it.position < 5.0
            } as Collection<MovingEntity>

            if (entitiesInRadius.isNotEmpty()) {
                // picks random entity from the list as a target
                val entity = entitiesInRadius.random()
                while (entity.position..position <= 1.0) {
                    val pos = entity.position
                    turn(pos)
                }

                // safes previous speed from the target entity
                val prevspeed = entity.speed
                // stole an Item from entity
                wanttosteal = true

                /* Unfortunately, this implementation did not exist at the time from the other group.
                 * main concept:
                 * ticket is an attribute of the entity and set to true
                 * when stealing the ticket we set the value to false for the entity (no ticket)
                 * and then we set out value to true (ticket)
                 *
                entity.ticket = false
                this.ticket = true
                *
                */

                // as a thief we get chased by the entity for a certain time.
                thread {
                    speed = 2.0 // running away
                    entity.speed = 2.0
                    entity.turn(position) // running behind
                    Thread.sleep(20000)
                    entity.speed = prevspeed // giving up and continuing doing what it was doing
                    entity.turn(Point(Random.nextInt(0, world.width), Random.nextInt(0, world.height)))

                    Thread.sleep(60000) //
                    wanttosteal = false
                    // this.ticket = false
                }

                if(lastPosition == position) {
                    do {
                        try {
                            // new random position
                            turn(
                                Point(
                                    Random.nextInt(0, world.width),
                                    Random.nextInt(0, world.height)
                                )
                            )
                            break
                        } catch (_: Exception) {}
                    } while(true)
                }
                lastPosition = position

            }
        }
    }

    companion object {
        const val TYPE_ID = "ThiefAgent"
    }
}






