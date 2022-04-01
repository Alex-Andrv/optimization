public class PowellMethod extends AbstractQuasiNewtonian {

    @Override
    protected Matrix getG(Matrix G, Vector delX, Vector delW) {
        Vector delXStar = delX.add(new Vector(G.multiply(delW)));
        return G.sub(delXStar.multiply(delXStar.transposed()).multiplyByConst(1 / delW.dotProduct(delXStar)));
    }
}
