package fr.corentinbringer.fleetlens.model;

import java.util.UUID;

public record UpsertOutcome(boolean created, UUID internalId) {
}
