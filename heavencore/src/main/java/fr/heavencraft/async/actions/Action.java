package fr.heavencraft.async.actions;

public interface Action
{
	void executeAction();

	void onSuccess();

	void onFailure();
}