package customapis;


import java.util.logging.Logger;

import com.provar.core.model.base.api.ValueScope;
import com.provar.core.testapi.ITestExecutionContext;
import com.provar.core.testapi.annotations.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@TestApi( title="Date"
        , summary=""
        , remarks=""
        , iconBase=""
        , defaultApiGroups={"Custom"}
        )
@TestApiParameterGroups(parameterGroups={
	    @TestApiParameterGroup(groupName="inputs", title="Inputs"),
	    @TestApiParameterGroup(groupName="result", title="Result"),
	    })
public class Date {
    
    @TestApiParameter(seq=1, 
            summary="The first parameter's summary.",
            remarks="",
            mandatory=true,
            parameterGroup="inputs")
    public String param1;
    
    @TestApiParameter(seq=10, 
            summary="The name that the result will be stored under.",
            remarks="",
            mandatory=true,
            parameterGroup="result")
    public String resultName;

    @TestApiParameter(seq=11, 
            summary="The lifespan of the result.",
            remarks="",
            mandatory=true,
            parameterGroup="result",
            defaultValue="Test")
    public ValueScope resultScope;

    /** 
     * Used to write to the test execution log.
     */
    @TestLogger
    public Logger testLogger;
    
    /** 
     * Provides access to facilities, mainly to set and get variable values.
     */
    @TestExecutionContext
    public ITestExecutionContext testExecutionContext;
    
    @TestApiExecutor
    public LocalDate calculateFutureDate(LocalDate startDate, Set<LocalDate> usHolidays) {
        LocalDate futureDate = startDate;
        int daysAdded = 0;

        while (daysAdded < 10) {
            futureDate = futureDate.plusDays(1);

            // Check if the day is a weekend or a holiday
            if (!(futureDate.getDayOfWeek() == DayOfWeek.SATURDAY || 
                  futureDate.getDayOfWeek() == DayOfWeek.SUNDAY || 
                  usHolidays.contains(futureDate))) {
                daysAdded++;
            }
        }

        return futureDate;
    }
//    String dummyResult = this.getClass().getName() + " result";
//    testExecutionContext.setValue(resultName, dummyResult, resultScope);
}
