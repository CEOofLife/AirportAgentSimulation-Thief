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
        // var countTicket = 0
        if (!wanttosteal) {

            @Suppress("unchecked_cast") val entitiesInRadius = world.entities.filter {
                it is MovingEntity && position..it.position < 5.0
            } as Collection<MovingEntity>

            if (entitiesInRadius.isNotEmpty()) {
                // picks random entity
                val entity = entitiesInRadius.random()
                while (entity.position..position <= 1.0) {
                    val pos = entity.position
                    turn(pos)
                }

                // prep infos about entity
                // val prevdirection = entity.direction
                val prevspeed = entity.speed
                wanttosteal = true //stole an Item
                /*
                 * wird noch implementiert
                entity.ticket = false
                this.ticket = true
                var countTicket =+ 1
                */

                thread {
                    speed = 2.0 // running away
                    entity.speed = 2.0
                    entity.turn(position) // running behind
                    Thread.sleep(20000)
                    entity.speed = prevspeed // giving up and continuing doing what it was doing
                    entity.turn(Point(Random.nextInt(0, world.width), Random.nextInt(0, world.height)))

                    Thread.sleep(60000) // nach einer minute wieder bereit zu stehlen
                    wanttosteal = false
                }



                if(lastPosition == position) {
                    do {
                        try {
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


                /*
                val securitygate = world.entities.filterIsInstance<securityGate>().firstOrNull ?: return // TODO: fill in right object name
                val securitygatepos = securitygate.getposition()
                turn(securitygatepos)
                */
            }
        }
    }

    companion object {
        const val TYPE_ID = "ThiefAgent"
    }
}
// macht Simulation an sich
// registrierung in plugin.txt

// annähern an entity, ablenken, ticket wert ändern
// move turn (x,y) 0 - world.width / .height
// speed: vektoren turn punkt
// simulation in world - entities (moving, static)
// direction - punkt ändern - hin turnen





