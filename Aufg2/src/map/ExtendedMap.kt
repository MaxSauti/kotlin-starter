package map

class ExtendedMap<K : Comparable<K>, V> : MutableMap<K, V> {

    private val data = mutableListOf<Entry<K, V>>()

    private val entryView = EntryView()
    private val keyView = KeyView()
    private val valueView = ValueView()


    // ------------------------------------------------------------
    // Interne Map-Entries
    // ------------------------------------------------------------

    private class Entry<K, V>(
        override val key: K,
        override var value: V
    ) : MutableMap.MutableEntry<K, V> {

        override fun setValue(newValue: V): V {
            val oldValue = this.value
            this.value = newValue
            return oldValue
            //TODO("Setze den neuen Wert und gib den vorherigen Wert zurück.")
        }
    }


    // ------------------------------------------------------------
    // View auf die Keys
    // ------------------------------------------------------------

    private inner class KeyView : MutableSet<K> {

        private inner class KeyIterator : MutableIterator<K> {

            private val iterator = data.iterator()

            override fun remove() {
                iterator.remove()
            }

            override fun next(): K {
                return iterator.next().key
            }

            override fun hasNext(): Boolean {
                return iterator.hasNext()
            }
        }


        override fun iterator(): MutableIterator<K> {
            return KeyIterator()
        }


        /*
         * Ein einzelner Key kann nicht hinzugefügt werden,
         * da der zugehörige Value fehlen würde.
         */
        override fun add(element: K): Boolean {
            throw UnsupportedOperationException()
        }


        /*
         * Entferne den Entry, dessen Key dem übergebenen
         * Element entspricht.
         */
        override fun remove(element: K): Boolean {
            val iterator = data.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (entry.key == element) {
                    iterator.remove()
                    return true
                }
            }
            return false
            //TODO("Entferne den passenden Entry aus data.")
        }


        override fun addAll(elements: Collection<K>): Boolean {
            throw UnsupportedOperationException()
        }


        /*
         * Entferne alle Entries, deren Key in elements vorkommt.
         */
        override fun removeAll(elements: Collection<K>): Boolean {
            var changed = false
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.key in elements) {
                    iterator.remove()
                    changed = true
                }
            }

            return changed
        }


        /*
         * Behalte nur Entries, deren Key in elements vorkommt.
         */
        override fun retainAll(elements: Collection<K>): Boolean {
            var changed = false
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.key !in elements) {
                    iterator.remove()
                    changed = true
                }
            }
            return changed
            //TODO("Entferne alle Entries, deren Key nicht in elements vorkommt.")
        }


        override fun clear() {
            data.clear()
        }


        override val size: Int
            get() = data.size


        override fun isEmpty(): Boolean {
            return data.isEmpty()
        }


        /*
         * Prüfe, ob ein Entry mit diesem Key existiert.
         */
        override fun contains(element: K): Boolean {
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.key == element) {
                    return true
                }
            }

            return false
            //TODO("Suche nach einem Entry mit passendem Key.")
        }


        override fun containsAll(elements: Collection<K>): Boolean {
            for (element in elements) {
                if (!contains(element)) {
                    return false
                }
            }

            return true
        }
    }


    // ------------------------------------------------------------
    // View auf die Values
    // ------------------------------------------------------------

    private inner class ValueView : MutableCollection<V> {

        private inner class ValueIterator : MutableIterator<V> {

            private val iterator = data.iterator()

            override fun remove() {
                iterator.remove()
            }

            override fun next(): V {
                return iterator.next().value
            }

            override fun hasNext(): Boolean {
                return iterator.hasNext()
            }
        }


        override fun iterator(): MutableIterator<V> {
            return ValueIterator()
        }


        /*
         * Ein einzelner Value kann nicht hinzugefügt werden,
         * da der zugehörige Key fehlen würde.
         */
        override fun add(element: V): Boolean {
            throw UnsupportedOperationException()
        }


        override fun addAll(elements: Collection<V>): Boolean {
            throw UnsupportedOperationException()
        }


        /*
         * Entferne den ersten Entry mit diesem Value.
         */
        override fun remove(element: V): Boolean {
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.value == element) {
                    iterator.remove()
                    return true
                }
            }

            return false
            //TODO("Entferne den ersten Entry mit passendem Value.")
        }


        /*
         * Entferne alle Entries, deren Value in elements vorkommt.
         */
        override fun removeAll(elements: Collection<V>): Boolean {
            var changed = false
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.value in elements) {
                    iterator.remove()
                    changed = true
                }
            }

            return changed
            //TODO("Entferne alle passenden Values.")
        }


        /*
         * Behalte nur Entries, deren Value in elements vorkommt.
         */
        override fun retainAll(elements: Collection<V>): Boolean {
            var changed = false
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.value !in elements) {
                    iterator.remove()
                    changed = true
                }
            }

            return changed
        }


        override fun clear() {
            data.clear()
        }


        override val size: Int
            get() = data.size


        override fun isEmpty(): Boolean {
            return data.isEmpty()
        }


        /*
         * Prüfe, ob mindestens ein Entry diesen Value besitzt.
         */
        override fun contains(element: V): Boolean {
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (entry.value == element) {
                    return true
                }
            }

            return false
            //TODO("Suche nach einem Entry mit passendem Value.")
        }


        override fun containsAll(elements: Collection<V>): Boolean {
            for (element in elements) {
                if (!contains(element)) {
                    return false
                }
            }

            return true
        }
    }


    // ------------------------------------------------------------
    // View auf die Entries
    // ------------------------------------------------------------

    private inner class EntryView :
        MutableSet<MutableMap.MutableEntry<K, V>> {

        override val size: Int
            get() = data.size


        override fun iterator():
                MutableIterator<MutableMap.MutableEntry<K, V>> {

            return data.iterator()
        }


        override fun contains(
            element: MutableMap.MutableEntry<K, V>
        ): Boolean {

            for (entry in data) {
                if (
                    entry.key == element.key &&
                    entry.value == element.value
                ) {
                    return true
                }
            }

            return false
        }


        override fun remove(
            element: MutableMap.MutableEntry<K, V>
        ): Boolean {

            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                if (
                    entry.key == element.key &&
                    entry.value == element.value
                ) {
                    iterator.remove()
                    return true
                }
            }

            return false
        }


        /*
         * Falls der Key bereits existiert:
         * - gleicher Value -> keine Änderung
         * - anderer Value -> Value ersetzen
         *
         * Falls der Key nicht existiert:
         * - neuen Entry hinzufügen
         */
        override fun add(
            element: MutableMap.MutableEntry<K, V>
        ): Boolean {
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (entry.key == element.key) {
                    if (entry.value == element.value) {
                        return false
                    }
                    entry.setValue(element.value)
                    return true
                }
            }

            data.add(Entry(element.key, element.value))
            return true
            //TODO("Füge den Entry ein oder aktualisiere den vorhandenen Value.")
        }


        override fun addAll(
            elements: Collection<MutableMap.MutableEntry<K, V>>
        ): Boolean {
            var changed = false
            for (element in elements) {
                if (add(element)) {
                    changed = true
                }
            }
            return changed
            //TODO("Füge alle Entries über add(...) hinzu.")
        }


        override fun clear() {
            data.clear()
        }


        override fun isEmpty(): Boolean {
            return data.isEmpty()
        }


        override fun containsAll(
            elements: Collection<MutableMap.MutableEntry<K, V>>
        ): Boolean {

            for (element in elements) {
                if (!contains(element)) {
                    return false
                }
            }

            return true
        }


        override fun removeAll(
            elements: Collection<MutableMap.MutableEntry<K, V>>
        ): Boolean {

            var changed = false
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()

                for (element in elements) {
                    if (
                        entry.key == element.key &&
                        entry.value == element.value
                    ) {
                        iterator.remove()
                        changed = true
                        break
                    }
                }
            }

            return changed
        }


        override fun retainAll(
            elements: Collection<MutableMap.MutableEntry<K, V>>
        ): Boolean {

            var changed = false
            val iterator = data.iterator()

            while (iterator.hasNext()) {
                val entry = iterator.next()
                var keep = false

                for (element in elements) {
                    if (
                        entry.key == element.key &&
                        entry.value == element.value
                    ) {
                        keep = true
                        break
                    }
                }

                if (!keep) {
                    iterator.remove()
                    changed = true
                }
            }

            return changed
        }
    }


    // ------------------------------------------------------------
    // MutableMap-Properties
    // ------------------------------------------------------------

    override val entries:
            MutableSet<MutableMap.MutableEntry<K, V>>
        get() = entryView


    override val keys: MutableSet<K>
        get() = keyView


    override val values: MutableCollection<V>
        get() = valueView


    override val size: Int
        get() = data.size


    // ------------------------------------------------------------
    // MutableMap-Methoden
    // ------------------------------------------------------------

    override fun isEmpty(): Boolean {
        return data.isEmpty()
    }


    override fun containsKey(key: K): Boolean {
        return keys.contains(key)
        //TODO("Prüfe über die KeyView, ob der Key existiert.")
    }


    override fun containsValue(value: V): Boolean {
        return values.contains(value)
        //TODO("Prüfe über die ValueView, ob der Value existiert.")
    }


    /*
     * Liefere den Value zum gegebenen Key.
     * Falls der Key nicht existiert, gib null zurück.
     */
    override fun get(key: K): V? {
        val iterator = data.iterator()

        while (iterator.hasNext()) {
            val entry = iterator.next()

            if (entry.key == key) {
                return entry.value
            }
        }

        return null
       //TODO("Suche den Entry mit passendem Key.")
    }


    /*
     * Falls der Key bereits existiert, ersetze den bisherigen Value.
     *
     * Rückgabe:
     * - alter Value, falls vorhanden
     * - null, falls der Key vorher nicht existierte
     */
    override fun put(key: K, value: V): V? {
        val iterator = data.iterator()

        while (iterator.hasNext()) {
            val entry = iterator.next()

            if (entry.key == key) {
                return entry.setValue(value)
            }
        }

        data.add(Entry(key, value))
        return null
        //TODO("Füge einen neuen Entry ein bzw. ersetze den vorhandenen Value.")
    }


    /*
     * Entferne den Entry mit dem gegebenen Key.
     *
     * Rückgabe:
     * - vorheriger Value
     * - null, falls der Key nicht existierte
     */
    override fun remove(key: K): V? {
        val oldVal = get(key) ?: return null
        keys.remove(key)
        return oldVal
        //TODO("Entferne den passenden Entry und gib seinen Value zurück.")
    }


    override fun putAll(from: Map<out K, V>) {
        for(entry in from) {
            put(entry.key, entry.value)
        }
        //TODO("Füge alle Key-Value-Paare aus from über put(...) ein.")
    }


    override fun clear() {
        data.clear()
    }


    // ------------------------------------------------------------
    // Erweiterung der normalen Map
    // ------------------------------------------------------------

    /*
     * Der kleinste Key bestimmt den zurückgegebenen Value.
     *
     * Warum ist K : Comparable<K> notwendig?
     */
    fun getMin(): V? {
        if(data.isEmpty()) {
            return null
        }
        var smallest = data[0]
        val iterator = data.iterator()
        iterator.next()

        while (iterator.hasNext()) {
            val entry = iterator.next()
            if(smallest.key > entry.key) {
                smallest = entry
            }
        }

        return smallest.value

        //TODO("Bestimme den kleinsten Key und gib den zugehörigen Value zurück.")
    }


    /*
     * Der größte Key bestimmt den zurückgegebenen Value.
     */
    fun getMax(): V? {
        if(data.isEmpty()) {
            return null
        }
        var greatest = data[0]
        val iterator = data.iterator()
        iterator.next()

        while (iterator.hasNext()) {
            val entry = iterator.next()
            if(greatest.key < entry.key) {
                greatest = entry
            }
        }

        return greatest.value

        //TODO("Bestimme den größten Key und gib den zugehörigen Value zurück.")
    }
}