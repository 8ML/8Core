/*
 * Copyright 2022 8ML (https://github.com/8ML)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
Created by @8ML (https://github.com/8ML) on 4/28/2021
*/

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
