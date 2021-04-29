/*
Created by Sander on 4/28/2021
*/

import club.mineplay.core.punishment.Punishment;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class PunishTime {

    enum TimeUnit {
        SECONDS(1000),
        MINUTES(1000 * 60),
        HOURS(1000 * 60 * 60),
        DAYS(1000 * 60 * 60 * 24),
        PERMANENT(-1);

        public int multiplier;

        TimeUnit(int multiplier) {
            this.multiplier = multiplier;
        }
    }

    private final long duration;

    private final TimeUnit unit;

    private final boolean permanent;

    public PunishTime(TimeUnit unit, long duration) {
        this.permanent = false;
        this.unit = unit;
        this.duration = duration * unit.multiplier;
    }

    public PunishTime(long duration) {

        this.duration = duration;
        this.permanent = duration == 0;

        final long maxSecondsMillis = (1000 * 60);
        final long maxMinutesMillis = (maxSecondsMillis * 60);
        final long maxHoursMillis = (maxMinutesMillis * 24);

        if (duration > maxSecondsMillis) {
            if (duration > maxMinutesMillis) {
                if (duration > maxHoursMillis) {
                    this.unit = TimeUnit.DAYS;
                } else {
                    this.unit = TimeUnit.HOURS;
                }
            } else {
                this.unit = TimeUnit.MINUTES;
            }
        } else {
            this.unit = TimeUnit.SECONDS;
        }

    }

    public PunishTime() {
        this.duration = 0L;
        this.permanent = true;
        this.unit = TimeUnit.PERMANENT;
    }

    public long getDuration() {
        return this.duration;
    }

    public TimeUnit getUnit() {
        return this.unit;
    }

    public double getTimeLeft() {

        double duration = Long.valueOf(this.duration).doubleValue();
        double d = duration / this.unit.multiplier;
        return BigDecimal.valueOf(d).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }

}