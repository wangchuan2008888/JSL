package jsl.utilities.random.mcintegration;

import jsl.utilities.distributions.Normal;
import jsl.utilities.math.FunctionIfc;
import jsl.utilities.random.rvariable.RVariableIfc;
import jsl.utilities.random.rvariable.UniformRV;
import jsl.utilities.statistic.Statistic;

import java.util.Objects;

/**
 * Provides for the integration of a 1-D function via Monte-Carlo sampling.
 * The user is responsible for providing a function that when evaluated at the
 * sample from the provided sampler will evaluate to the desired integral over
 * the range of support of the supplied sampler.
 * <p>
 * The sampler must have the range of support as the desired integration and the function's domain (inputs)
 * must be consistent with the range (output) of the sampler.
 * <p>
 * As an example, suppose we want the evaluation of the integral of g(x) over the range from a to b.
 * If the user selects the sampler as U(a,b) then the function to supply for the integration is NOT g(x).
 * The function should be h(x) = (b-a)*g(x).
 * <p>
 * In general, if the sampler has pdf, w(x), over the range a to b. Then, the function to supply for integration
 * is h(x) = g(x)/w(x). Again, the user is responsible for providing a sampler that provides values over the interval
 * of integration.  And, the user is responsible for providing the appropriate function, h(x), that will result
 * in their desired integral.  This flexibility allows the user to specify h(x) in a factorization that supports an
 * importance sampling distribution as the sampler.
 * <p>
 * The user specifies a desired error bound, an initial sample size, and a maximum sample size limit.  The initial
 * sample size is used to generate a pilot sample from which an estimate of the number of samples needed to meet
 * the relative precision criteria. Let's call estimated sample size, m.  If after the initial sample is taken, the error is met the
 * evaluation stops; otherwise, the routine will sample until the error criteria is met or the
 * min(m, maximum sample size limit).
 * <p>
 * The user can check if the error criteria was met after the evaluation. If it is not met, the user can
 * adjust the initial sample size, desired error, or maximum sample size and run another evaluation.
 * The statistics associated with the estimate are readily available. The evaluation will automatically utilize
 * antithetic sampling to reduce the variance of the estimates unless the user specifies not to do so. In the case of
 * using antithetic sampling, the sample size refers to the number of independent antithetic pairs observed. Thus, this
 * will require two function evaluations at each observation. The user can consider the implication of the cost of
 * function evaluation versus the variance reduction obtained. The user may
 * also reset the underlying random number stream if a reproducible result is desired within the same execution frame.
 * By default, the underlying random number stream is not reset with each invocation of the evaluate() method.
 * The default confidence level is 99 percent.
 *
 * Be aware that small desired absolute error may result in large execution times.
 */
public class MC1DIntegration extends MCIntegration {

    protected final FunctionIfc myFunction;
    protected final RVariableIfc mySampler;
    protected RVariableIfc myAntitheticSampler;

    /**
     *
     * @param function the representation of h(x), must not be null
     * @param sampler  the sampler over the interval, must not be null
     */
    public MC1DIntegration(FunctionIfc function, RVariableIfc sampler) {
        this(function, sampler, true);
    }

    /**
     *
     * @param function the representation of h(x), must not be null
     * @param sampler  the sampler over the interval, must not be null
     * @param antitheticOptionOn  true represents use of antithetic sampling
     */
    public MC1DIntegration(FunctionIfc function, RVariableIfc sampler, boolean antitheticOptionOn) {
        Objects.requireNonNull(sampler, "The RVariableIfc was null!");
        Objects.requireNonNull(function, "The FunctionIfc was null!");
        this.myFunction = function;
        this.mySampler = sampler;
        if (antitheticOptionOn) {
            myAntitheticSampler = mySampler.newAntitheticInstance();
        }
        setConfidenceLevel(0.99);
    }

    @Override
    protected double sample(int n) {
        if (resetStreamOptionOn) {
            mySampler.resetStartStream();
            if(isAntitheticOptionOn()){
                myAntitheticSampler.resetStartStream();
            }
        }
        double y;
        for (int i = 1; i <= n; i++) {
            if (isAntitheticOptionOn()) {
                double y1 = myFunction.fx(mySampler.sample());
                double y2 = myFunction.fx(myAntitheticSampler.sample());
                y = (y1 + y2) / 2.0;
            } else {
                y = myFunction.fx(mySampler.sample());
            }
            statistic.collect(y);
            if (checkStoppingCriteria()) {
                return statistic.getCount();
            }
        }
        return statistic.getCount();
    }

    @Override
    public boolean isAntitheticOptionOn() {
        return myAntitheticSampler != null;
    }

    public static void main(String[] args) {

        class SinFunc implements FunctionIfc {

            public double fx(double x) {

                return (Math.PI*Math.sin(x));
            }

        }

        SinFunc f = new SinFunc();
        MC1DIntegration mc = new MC1DIntegration(f, new UniformRV(0.0, Math.PI));

        mc.runInitialSample();
        System.out.println(mc);
        System.out.println();
        mc.evaluate();
        System.out.println(mc);

    }


}
