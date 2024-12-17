package day16;

import java.util.Objects;

final class Visit<T> implements Comparable<Visit<T>> {
    public final T value;
    public final int accumulatedScore;
    public final Visit<T> parent;

    Visit(int accumulatedScore, T value, Visit<T> parent) {
        this.value = value;
        this.accumulatedScore = accumulatedScore;
        this.parent = parent;
    }

    @Override public int compareTo(Visit<T> o) {
        return Integer.compare(accumulatedScore, o.accumulatedScore);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Visit) obj;
        return Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return accumulatedScore+" "+value.toString();
    }

}
