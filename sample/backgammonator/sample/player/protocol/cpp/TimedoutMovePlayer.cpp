#ifdef WIN32
#include <windows.h>
#else
#include <unistd.h>
#define Sleep(x) usleep((x)*1000)
#endif


int main() {
    Sleep(2200);
	return 0;
}
