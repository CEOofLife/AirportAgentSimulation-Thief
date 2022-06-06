import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.AirportAgentSimulation
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.config.ConfigurationFormatException
import dhbw.sose2022.softwareengineering.airportagentsim.simulation.api.plugin.Plugin

class ThiefPlugin : Plugin {

    override fun activate() {
        try {
            AirportAgentSimulation.registerEntity(this, ThiefAgent.TYPE_ID, ThiefAgent::class.java )
        } catch (exception: ConfigurationFormatException) {
            exception.printStackTrace()
        }
    }

}