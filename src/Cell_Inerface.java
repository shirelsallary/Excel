public interface Cell_Inerface {

        /**
         * Return the input text (aka String) this cell was init by (without any computation).
         * @return
         */
        String getData();

        /** Changes the underline string of this cell
         *  */
        void setData(String s);


        /**
         * Returns the type of this cell {TEXT,NUMBER, FORM, ERR_CYCLE_FORM, ERR_WRONG_FORM}
         * @return an int value (as defined in Ex2Utils)
         */
        public int getType();

        /**
         * Changes the type of this Cell {TEXT,NUMBER, FORM, ERR_CYCLE_FORM, ERR_WRONG_FORM}
         * @param t an int type value as defines in Ex2Utils.
         */
        public void setType(int t);
        /**
         * Computes the natural order of this entry (cell) in case of a number or a String =0, else 1+ the max of all dependent cells.
         * @return an integer representing the "number of rounds" needed to compute this cell (using an iterative approach)..
         */
        public int getOrder();
        /**
         * Changes the order of this Cell
         * @param t
         */
        public void setOrder(int t);
    }

