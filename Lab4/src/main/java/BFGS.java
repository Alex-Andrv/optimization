public class BFGS extends AbstractQuasiNewtonian {

    @Override
    protected Matrix getG(Matrix G, Vector delX, Vector delW) {
        Matrix firstArg = delX.multiply(delX.transposed()).delByConst(delW.dotProduct(delX));
        double ro = new Vector(G.multiply(delW)).dotProduct(delW);
        Matrix secondArg = G.multiply(delW).multiply(delW.transposed()).multiply(G.transposed()).
                delByConst(ro);
        Vector r = new Vector(G.multiply(delW).delByConst(ro).
                sub(delX.delByConst(delX.dotProduct(delW))));
        Matrix thirdArg = r.multiply(r.transposed()).multiplyByConst(ro);

        return G.sub(firstArg).sub(secondArg).add(thirdArg);
    }
}
