package uk.theretiredprogrammer.clifiletypes.sh;

public class ParamDefinition {
    
      private final int paramno;
      private final int fieldno;
      private final String alternatives;

       public ParamDefinition(int paramno, int fieldno, String alternatives) {
          this.paramno = paramno;
          this.fieldno = fieldno;
          this.alternatives  = alternatives;
      }

        public int getParamno() {
            return paramno;
        }

        public int getFieldno() {
            return fieldno;
        }

        public String getAlternatives() {
            return alternatives;
        }
}
