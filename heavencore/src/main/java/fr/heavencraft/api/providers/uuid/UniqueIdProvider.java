package fr.heavencraft.api.providers.uuid;

import java.util.UUID;

import fr.heavencraft.api.providers.Provider;
import fr.heavencraft.exceptions.UserNotFoundException;

public interface UniqueIdProvider extends Provider
{
	String getNameFromUniqueId(UUID uuid) throws UserNotFoundException;
}