package fr.heavencraft.heavenproxy;

public enum Server
{
	Nexus("nexus", "Nexus", "Nex"),
	SemiRP("semirp", "Semi-RP", "SRP"),
	Origines("origines", "Origines", "Ori"),
	Creatif("creative", "Créatif", "Créa"),
	Build("build", "Build", "Créa"),
	Fun("fun", "Fun", "Fun"),
	Infected("infected", "Infected", "Inf"),
	Musee("musee", "Musée", "Mus"),
	MarioKart("mariokart", "Mario Kart", "MK"),
	TNTRun("tntrun", "TNT Run", "TNT"),
	UltraHardcore("ultrahard", "Ultra-Hardcore", "UH"),
	Paintball("paintball", "Paintball", "PB"),
	HungerGames("hungergames", "Hunger Games", "HG"),
	UnknownServer("", "Veuillez insérer la disquette 22.", "???");

	private final String name;
	private final String displayName;
	private final String prefix;

	Server(String name, String displayName, String prefix)
	{
		this.name = name;
		this.displayName = displayName;
		this.prefix = prefix;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public static Server getUniqueInstanceByName(String name)
	{
		for (Server server : Server.values())
		{
			if (server.name.equals(name))
			{
				return server;
			}
		}

		return UnknownServer;
	}
}