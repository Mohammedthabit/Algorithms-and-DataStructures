package gad.simplehash;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Hashtable<K, V> {
    private List<Pair<K, V>>[] table;
    private int[] a;

    @SuppressWarnings("unchecked")
    public Hashtable(int minSize, int[] a) {
        this.a = a;
        table = (List<Pair<K, V>>[]) new List[getNextPowerOfTwo(minSize)];
        for (int i = 0; i < table.length; i++){
            table[i] = new ArrayList<>();
        }
    }

    public List<Pair<K, V>>[] getTable() {
        return table;
    }

    public static int getNextPowerOfTwo(int i) {
        if (i <= 0) {
            return 1;
        } else {
            int value = 1;
            while (value < i) {
                value *= 2;
            }
            return value;
        }
    }

    public static int fastModulo(int i, int divisor) {
        return i & (divisor - 1);
    }

    private byte[] bytes(K k) {
        return k.toString().getBytes(StandardCharsets.UTF_8);
    }

    public int h(K k, ModuloHelper mH) {
        byte[] x = bytes(k);
        int sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum = sum + (x[i] & 0xFF) * a[i % a.length];
        }
        return fastModulo(sum, table.length);
    }

    public void insert(K k, V v, ModuloHelper mH) {
        int i = h(k, mH);
        List<Pair<K, V>> list = table[i];
        list.add(new Pair<>(k, v));
    }

    public boolean remove(K k, ModuloHelper mH) {
        boolean isRemoved = false;
        int index = h(k, mH);
        List<Pair<K, V>> list = table[index];

        for (int i = 0; i < list.size(); i++) {

            Pair<K, V> pair = list.get(i);

            if (pair.one().equals(k)) {
                list.remove(i);
                isRemoved = true;
                i--;
            }

        }

        return isRemoved;
    }

    public Optional<V> find(K k, ModuloHelper mH) {
        int index = h(k, mH);
        List<Pair<K, V>> list = table[index];

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).one().equals(k)){
                return Optional.of(list.get(i).two());
            }
        }

        return Optional.empty();
    }

    public List<V> findAll(K k, ModuloHelper mH) {
        int index = h(k, mH);
        List<Pair<K, V>> list = table[index];
        List<V> found = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).one().equals(k)){
                found.add(list.get(i).two());
            }
        }

        return found;
    }

    public Stream<Pair<K, V>> stream() {
        return Stream.of(table).filter(Objects::nonNull).flatMap(List::stream);
    }

    public Stream<K> keys() {
        return stream().map(Pair::one).distinct();
    }

    public Stream<V> values() {
        return stream().map(Pair::two);
    }
}