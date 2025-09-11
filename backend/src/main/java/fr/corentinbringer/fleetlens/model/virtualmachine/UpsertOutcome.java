package fr.corentinbringer.fleetlens.model.virtualmachine;

import java.util.UUID;

public record UpsertOutcome(boolean created, UUID internalId) {
}
