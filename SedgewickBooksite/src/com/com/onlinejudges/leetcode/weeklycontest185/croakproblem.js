/**
 * @param {string} croakOfFrogs
 * @return {number}
 */
var minNumberOfFrogs = function(croakOfFrogs) {
    const croakString = 'croak';
    let numStateMachines = 0;
    let problem = false;
    function croakProgressOk(currStateChar, nextChar) {
        if(croakString.indexOf(nextChar) === croakString.indexOf(currStateChar) + 1){
            return nextChar;
        }
        if (currStateChar === 'k' && nextChar === 'c') {
            return 'c';
        }
        return false;
    }
    
    numStateMachines = ['c'];// first state machine
    
    for(var i = 1 ;i < croakOfFrogs.length; i++){
        var ch = croakOfFrogs[i];
        // console.log('looking at char', i, ' ch: ', ch); 
        let transitionFound = false;
        for(var j = 0; j < numStateMachines.length ; j++) {
            const stateMachine = numStateMachines[j];
            if(croakProgressOk(stateMachine, ch)) {
                // console.log('made progress for idx: ', j, " ch: ", ch);
                numStateMachines[j] = croakProgressOk(stateMachine, ch);
                transitionFound = true;
                break;
            } else {
                // console.log('not ok: ', stateMachine,"   ", ch);
            }
        }
        if(!transitionFound) {
            if (ch != 'c') {
                // console.log('transition problem found: ', numStateMachines); 
                // console.log('ch under scan: ', ch);
                problem = true;
                return -1;
            } else {
                // console.log('making new state machine!');
                numStateMachines.push('c');
            }
        }
    }

    for(var f=0; f< numStateMachines.length; f++){
        let s = numStateMachines[f];
        if (s != 'k'){
            // console.log('not all ended with k: ', numStateMachines); 
            problem = true;
        }
    }
   
    if (problem) {
        return -1;
    }

        

    return numStateMachines.length;

};