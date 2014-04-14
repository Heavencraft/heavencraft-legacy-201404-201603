package fr.heavencraft.NavalConflicts.Handlers.Player;

public enum Team {
	Blue("Blue"), Red("Red"), None("None"), Global("Global");

	private String string;

	private Team(String s)
	{
		string = s;
	}

	@Override
	public String toString() {
		return string;
	}

};
