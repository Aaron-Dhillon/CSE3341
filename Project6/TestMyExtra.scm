#!/usr/bin/env scheme-srfi-7

(program
  (requires srfi-34 srfi-78)
  (files "myfns.scm")
  (code

(define myinterpreter-with-exception-handling
  (lambda (progs)
    (call-with-current-continuation
      (lambda (k)
        (with-exception-handler
          (lambda (x)
            (display x)
            (newline)
            (k "Error")
			(newline))
          (lambda () (plan progs)))))))

(define (main args)
  (check-set-mode! 'report-failed)
  (display "Grading myfns.ss")
  (newline)

	(check (myinterpreter-with-exception-handling
           '(planProg (planLet a (planFunction b (planAdd b b)) (a 5))))
         => 10)
	
	(check (myinterpreter-with-exception-handling
           '(planProg (planLet a (planFunction b (planAdd b b)) (planLet a 1 (planMul a a)))))
         => 1)
		 
	(check (myinterpreter-with-exception-handling
           '(planProg (planLet a (planFunction b (planAdd b b)) (planAdd (a 5) (a 5)))))
         => 20)
	
	(check (myinterpreter-with-exception-handling
           '(planProg (planLet a (planFunction b (planAdd b b)) (planLet a (planFunction b (planMul b b)) (a 5)))))
         => 25)
		 

  (display "Grades:")
  (check-report))))