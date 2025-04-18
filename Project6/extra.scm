;; Test Cases for the extra credit

(define c1 '(planProg (planLet a (planFunction b (planAdd b b)) (a 5))))
(define c2 '(planProg (planLet a (planFunction b (planAdd b b)) (planLet a 1 (planMul a a)))))
(define c3 '(planProg (planLet a (planFunction b (planAdd b b)) (planAdd (a 5) (a 5)))))
(define c4 '(planProg (planLet a (planFunction b (planAdd b b)) (planLet a (planFunction b (planMul b b)) (a 5)))))


(define e1 10)
(define e2 1)
(define e3 20)
(define e4 25)




(define (test x) 
	(load "myfns.scm")
	(display 
		(string-append 
			"Expected:\n" 
			(number->string 
				(eval 
					(string->symbol 
						(string-append 
							"e" 
							(number->string x)
						)
					)
					(interaction-environment)
				)
			) 
			"\nYour output:\n"
		)
	) 
	(plan 
		(eval
			(string->symbol (string-append "c" (number->string x)))
			(interaction-environment)
		)
	)
)
