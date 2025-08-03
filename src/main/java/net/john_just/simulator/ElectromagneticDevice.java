package net.john_just.simulator;

import java.util.ArrayList;
import java.util.List;

public abstract class ElectromagneticDevice extends Component {
    protected final List<Contact> contacts = new ArrayList<>();
    protected final List<ContactType> types = new ArrayList<>();
    protected final double thresholdVoltage = 15.0; // Напряжение срабатывания
    private boolean previousActivated = false;
    private double deactivateTimer = 0.0;
    private final double releaseDelay = 0.05; // 50 мс "инерции" реле

    protected Terminal coilA;
    protected Terminal coilB;

    public ElectromagneticDevice(int contactCount, List<ContactType> types) {
        super(contactCount * 2 + 2); // +2 на катушку
        this.types.addAll(types);

        for (int i = 0; i < contactCount; i++) {
            Terminal a = getTerminals().get(i);
            Terminal b = getTerminals().get(i + contactCount);
            contacts.add(new Contact(a, b, types.get(i)));
        }

        this.coilA = getTerminals().get(contactCount * 2);
        this.coilB = getTerminals().get(contactCount * 2 + 1);
    }

    @Override
    public void update(double time) {
        double u = coilA.getVoltage() - coilB.getVoltage();
        boolean shouldBeActive = Math.abs(u) >= 15.0;

        if (shouldBeActive) {
            previousActivated = true;
            deactivateTimer = 0.0;
        } else if (previousActivated) {
            deactivateTimer += 1.0 / 60.0; // 60 Гц обновление (примерно)
            if (deactivateTimer >= releaseDelay) {
                previousActivated = false;
            }
        }

        for (Contact contact : contacts) {
            contact.setActivated(previousActivated);
        }
    }
}
