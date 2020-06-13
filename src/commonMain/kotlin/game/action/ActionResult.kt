package game.action

sealed class ActionResult

object InProgress : ActionResult()

object Succeeded : ActionResult() // its done

object Failed : ActionResult() // hard failed, no suggestions
