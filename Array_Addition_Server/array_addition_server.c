// Libraries that are used are included
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <pthread.h>

// Global variables are created here
#define DATA_SIZE 100
int PORT_NUMBER = 60000;
char INPUT_STRING[DATA_SIZE];
int FIRST_ARRAY[DATA_SIZE];
int SECOND_ARRAY[DATA_SIZE];
int CARRY_ARRAY[DATA_SIZE];
int RESULT_ARRAY[DATA_SIZE];
pthread_t THREAD_ARRAY[DATA_SIZE];
int carryFlag = 0;

// This function replaces multi spaces to single space
int multiSpaceToSingle(char* input){
    int i;
    int length = strlen(input);
    int index = 0;
    int flag = 0;
    int tabError = 0;

    // Replaces tabs. If you want to accept tab characters you can uncomment this 'for' block!
    /*
    for (int i = 0; i < length; ++i) {
        if (input[i] == '\t') {
            input[i] = ' ';
        }
    }
    */

   // If input string contains tab characters, assign tabError as 1 for returning Tab Error.
   for (int i = 0; i < length; ++i) {
        if (input[i] == '\t') {
            tabError = 1;
        }
    }
   
    // This code block assigns flag as 1 when a space character is seen. If flag is equal to 1 and the next character is 
    // also space then just increment i variable. When a non-space character is seen, assign flag as 0 and assign the current
    // character to input's current index.
    for (i = 0; i < length; i++) {
        if (input[i] == ' ') {
            if (flag == 0) {
                input[index++] = input[i];
                flag = 1;
            }
        } else {
            input[index++] = input[i];
            flag = 0;
        }
    }

    // Add null pointer to the end of the input.
    input[index] = '\0';
    return tabError;
}

// This function converts string which is generated from multiSpaceToSingle function to integer array.
int stringToIntArray(char input[], int first_second){
    
    int array_counter = 0;
    char token[4];
    int i = 0;

    // If the first character of the input is a space, it skips that character.
    if (*input == ' ')
    {
        input++;
    }

    // Splits the string by single space and assigns it to an integer array.
    while (*input != '\0') 
    {
        memset(&token, '\0', sizeof(token));
        if (*input == ' ') {
            input++;
        }

        while (*input != ' ' && *input != '\0') {
            token[i++] = *input;
            input++;
        }
    
        token[i] = '\0';
        i=0;

        if (token[0] != '\0')
        {
            // According to the first_second parameter, which specifies the order of the received input, 
            // it is determined which array to assign to.
            if (first_second == 1)
            {
                FIRST_ARRAY[array_counter++] = atoi(token);
            }
            else
            {
                SECOND_ARRAY[array_counter++] = atoi(token);
            }
        }  
    } 
    return array_counter;
}

// The last element of the input array is considered as LSB. As a result of this approach, 
// the size of the input array is increased by one and the first index is emptied. 
// In this way, in case of an overflow, the first index is updated to one and the carry flag is set to one.
// Then all elements are written to the Result Array starting from the first index. 
void calculateResultArray(int size)
{
    int TEMP_RESULT_ARRAY[size + 1];
    int TEMP_CARRY_ARRAY[size + 1];

    TEMP_RESULT_ARRAY[0] = 0;
    TEMP_CARRY_ARRAY[0] = 0;


    for (size_t i = 0; i < size; i++)
    {
        TEMP_RESULT_ARRAY[i + 1] = RESULT_ARRAY[i];
        TEMP_CARRY_ARRAY[i + 1] = CARRY_ARRAY[i];
    }
    
    // If the index that does not create a carry when it is summed, creates a carry when the next index is summed and 
    // its carry is added, the variable i is decremented by two and checked again.
    for (size_t i = 1; i < size + 1; i++)
    {
        if (TEMP_CARRY_ARRAY[i] == 1)
        {
            if (TEMP_RESULT_ARRAY[i - 1] == 999)
            {
                TEMP_RESULT_ARRAY[i - 1] = 0;
                TEMP_CARRY_ARRAY[i - 1] = 1;
                
                i = i - 2;
            }
            else
            {
                TEMP_RESULT_ARRAY[i - 1] = TEMP_RESULT_ARRAY[i - 1] + 1;
            }
            TEMP_CARRY_ARRAY[i] = 0;
        }
    }

    for (size_t i = 0; i < size + 1; i++)
    {
        RESULT_ARRAY[i] = TEMP_RESULT_ARRAY[i + 1];
        CARRY_ARRAY[i] = TEMP_CARRY_ARRAY[i + 1];
    }

    if(TEMP_RESULT_ARRAY[0] == 1){
        carryFlag = 1;
    }
}

// This function converts array's elements to string with one space between them.
char* convertIntArrayToString(const int *arr, int size) {
    char *result = (char*)malloc(DATA_SIZE);
    int index = 0;
    for (int i = 0; i < size; ++i) {
        if (i != 0) {
            result[index++] = ' ';
        }
        index += sprintf(&result[index], "%d", arr[i]);
    }
    return result;
}

// It returns 0 if input string contains non-numeric characters, otherwise return 1
int checkNumeric(const char *str) {
    while (*str) {
        
        if ((isdigit(*str) == 0) && *str != ' ' && *str != '\0') {
                return 0;
        }
        str++;
    }
    return 1; 
}

// This function checks whether the numbers entered for addition are longer than 3 digits.
int checkDigit(const char *str) {
    int digitCount = 0;

    while (*str) {
        if (isdigit(*str) != 0) {
            digitCount++;
            if (digitCount > 3) {
                return 0; 
            }
        }else if (*str == ' ') {  
            digitCount = 0;
        } 
        str++;
    }
    return 1; 
}

// The following function, which will be used by threads, updates the result and carry arrays 
// by adding the relevant indexes of the array according to the parameter it receives.
void *threeBitAdder(void *indexOfArray)
{
    int index = *((int *)indexOfArray);
    int sum = FIRST_ARRAY[index] + SECOND_ARRAY[index];
    if (sum < 999)
    {
        RESULT_ARRAY[index] = sum;
    }
    else
    {
        CARRY_ARRAY[index] = 1;
        RESULT_ARRAY[index] = sum - 1000;
    }
    pthread_exit(NULL);
}

int main() {
    int server_socket, client_socket;
    struct sockaddr_in server_addr, client_addr;
    socklen_t addr_size;

    int FIRST_ARRAY_SIZE = 0;
    int SECOND_ARRAY_SIZE = 0;

    // The server socket is created according to the port number and the connection is established.
    server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (server_socket < 0) {
        perror("Server socket cannot be created!");
        exit(EXIT_FAILURE);
    }

    memset(&server_addr, '\0', sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    server_addr.sin_port = htons(PORT_NUMBER);

    if (bind(server_socket, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
        perror("Connection failure");
        exit(EXIT_FAILURE);
    }

    if (listen(server_socket, 10) == -1) {
        perror("Listening failure");
        exit(EXIT_FAILURE);
    }
    printf("Server listens Port %d \n", PORT_NUMBER);

    addr_size = sizeof(client_addr);
    client_socket = accept(server_socket, (struct sockaddr*)&client_addr, &addr_size);
    if (client_socket < 0) {
        perror("Connection could not accepted!");
        exit(EXIT_FAILURE);
    }
    printf("Connection accepted. Waiting for a message...\n");

    //-------------------------------------------------first input-------------------------------------------------

    // Welcome message is sent to the user.
    char welcome_message [] = "Hello, this is Array Addition Server! \n"; 
    if (send(client_socket, welcome_message, strlen(welcome_message), 0) < 0) {
        perror("Message could not be sent");
        exit(EXIT_FAILURE);
    }

    // The first input is requested from the user.
    char take_first_input_message [] = "Please enter the first array for addition: "; 
    if (send(client_socket, take_first_input_message, strlen(take_first_input_message), 0) < 0) {
        perror("Message could not be sent");
        exit(EXIT_FAILURE);
    }

    // The first input received from the user is assigned to the corresponding string.
    memset(INPUT_STRING, '\0', sizeof(INPUT_STRING));
    if (recv(client_socket, INPUT_STRING, DATA_SIZE, 0) < 0) {
        perror("Message could not be recieved");
        exit(EXIT_FAILURE);
    }

    // The received input is checked in the following if blocks and the error flag and error message 
    // are updated as a result of these checks. If there is an error in the input as a result of these checks,
    // an error message is sent to the user and the connection is closed. 
    char error_message [1000]; 
    int errorFlag = 0;
    strcpy(error_message, "");

    // It is checked whether the input exceeds the data size.
    if(INPUT_STRING[DATA_SIZE - 1] != '\0' && INPUT_STRING[DATA_SIZE - 1] != '\n'){
        strcat(error_message, "ERROR: Input size must be under 100!\n");
        errorFlag = 1;
    }

    // Input is cleared by removing end-of-line characters from the string.
    if(strcspn(INPUT_STRING, "\n") != DATA_SIZE){
        INPUT_STRING[strcspn(INPUT_STRING, "\n")] = '\0';
    }
    if(strcspn(INPUT_STRING, "\r") != DATA_SIZE){
        INPUT_STRING[strcspn(INPUT_STRING, "\r")] = '\0';
    }

    // Multiple spaces in the input are converted to single spaces. 
    // At this time, it is checked whether the input contains a tab character.
    if(multiSpaceToSingle(INPUT_STRING) == 1){
        strcat(error_message, "ERROR: The input includes tab charcter!\n");
        errorFlag = 1;
    }

    // It is checked whether the digits in the input are more than three digits.
    if(checkDigit(INPUT_STRING) == 0){
        strcat(error_message, "ERROR: The numbers you entered between spaces must not exceed three digits!\n");
        errorFlag = 1;
    }

    // It checks whether the input contains a non-numeric character.
    if(checkNumeric(INPUT_STRING) == 0){
        strcat(error_message, "ERROR: The inputted integer array contains non-integer characters. You must input only integers and empty spaces to separate inputted integers!\n");
        errorFlag = 1;
    }
   
    // If there is no error in the input, the message that the input was successful is sent to the user.
    if(errorFlag == 0)
    {
        FIRST_ARRAY_SIZE = stringToIntArray(INPUT_STRING, 1);
        strcat(error_message, "The first input you entered meet the conditions.\n");
    }
    if (send(client_socket, error_message, strlen(error_message), 0) < 0) {
        perror("Message could not be sent");
        exit(EXIT_FAILURE);
    }

    // If there is an error in the input, the connection is terminated.
    if(errorFlag == 1)
    {
        close(client_socket);
        close(server_socket);
        exit(EXIT_FAILURE);
    }

    //-------------------------------------------------second input-------------------------------------------------

    // All the steps applied for the first input are also applied for the second input.

    char take_second_input_message [] = "Please enter the second array for addition: "; 
    if (send(client_socket, take_second_input_message, strlen(take_second_input_message), 0) < 0) {
        perror("Message could not be sent");
        exit(EXIT_FAILURE);
    }

    memset(INPUT_STRING, '\0', sizeof(INPUT_STRING));
    if (recv(client_socket, INPUT_STRING, DATA_SIZE, 0) < 0) {
        perror("Message could not be received");
        exit(EXIT_FAILURE);
    }

    char error_message2 [1000]; 
    strcpy(error_message2, "");

    if(INPUT_STRING[DATA_SIZE - 1] != '\0' && INPUT_STRING[DATA_SIZE - 1] != '\n'){
        strcat(error_message2, "ERROR: Input size must be under 100!\n");
        errorFlag = 1;
    }

    if(strcspn(INPUT_STRING, "\n") != DATA_SIZE){
        INPUT_STRING[strcspn(INPUT_STRING, "\n")] = '\0';
    }
    if(strcspn(INPUT_STRING, "\r") != DATA_SIZE){
        INPUT_STRING[strcspn(INPUT_STRING, "\r")] = '\0';
    }
    
    if(multiSpaceToSingle(INPUT_STRING) == 1){
        strcat(error_message2, "ERROR: The input includes tab charcter!\n");
        errorFlag = 1;
    }

    if(checkDigit(INPUT_STRING) == 0){
        strcat(error_message2, "ERROR: The numbers you entered between spaces must not exceed three digits!\n");
        errorFlag = 1;
    }

    if(checkNumeric(INPUT_STRING) == 0){
        strcat(error_message2, "ERROR: The inputted integer array contains non-integer characters. You must input only integers and empty spaces to separate inputted integers!\n");
        errorFlag = 1;
    }

    if(errorFlag == 0)
    {
        SECOND_ARRAY_SIZE = stringToIntArray(INPUT_STRING, 2);
        strcat(error_message2, "The second input you entered meet the conditions.\n");
    }

    // It is checked whether the inputs contain an equal number of values to be summed.
    if(FIRST_ARRAY_SIZE != SECOND_ARRAY_SIZE)
    {
        strcat(error_message2, "ERROR: First array size and second array size is not equal!\n");
        errorFlag = 1;
    }
    
    //-------------------------------------------------calculating and sending result-------------------------------------------------

    int indexes[FIRST_ARRAY_SIZE];
    int i, status;

    // For each index, child threads are created to perform the addition operation.
    for (size_t i = 0; i < FIRST_ARRAY_SIZE; i++)
    {
        indexes[i] = i;
        status = pthread_create(&THREAD_ARRAY[i], NULL, threeBitAdder, &indexes[i]);
        if (status) {
            printf("ERROR! Thread creation has failed: %d\n", status);
            break;
        }
    }

    // The join command is used to ensure that the parent thread does not run until the child threads are completed.
    for (size_t i = 0; i < FIRST_ARRAY_SIZE; i++)
    {
        pthread_join(THREAD_ARRAY[i], NULL);
    }
    
    // The correct result is calculated by adding the carries in the Carry Array to the Result Array.
    calculateResultArray(FIRST_ARRAY_SIZE);

    // The integer result we get in Result Array is converted to string and combined with result messages and sent to the user.
    char* resultArray = convertIntArrayToString(RESULT_ARRAY, FIRST_ARRAY_SIZE);  
    char resultString[1000];

    strcpy(resultString, "");
    strcat(resultString, "The result of array addition are given below: \n");

    // If result is more than data size and carry flag is 1, Result Array is not updated, 
    // only 1 is added to the beginning of result string.
    if (carryFlag == 1)
    {
        strcat(resultString, "1 ");
    }
    strcat(resultString, resultArray);
    strcat(resultString, "\n");
    strcat(resultString, "Thank you for Array Addition Server! Good Bye! \n");

    // If the inputs are correct, the result is sent to the user,  
    // If there is an error in the inputs, an error message is sent to the user.
    if (errorFlag == 0)
    {
        if (send(client_socket, resultString, strlen(resultString), 0) < 0) {
            perror("Message could not be sent");
            exit(EXIT_FAILURE);
        }
    }
    else
    {
        if (send(client_socket, error_message2, strlen(error_message2), 0) < 0) {
            perror("Message could not be sent");
            exit(EXIT_FAILURE);
        }
    }
    
    // When the operations are completed, the connection is closed.
    close(client_socket);
    close(server_socket);

    return 0;
}